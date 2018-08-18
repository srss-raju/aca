package us.deloitteinnovation.aca.batch.objectstore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import us.deloitteinnovation.aca.entity.IrsErrorCodeOwnerMapEntity;
import us.deloitteinnovation.aca.entity.IrsRecordDetails1095B;
import us.deloitteinnovation.aca.entity.IrsTransmissionDetails;
import us.deloitteinnovation.aca.entity.IrsTransmissionStatus;
import us.deloitteinnovation.aca.repository.IrsErrorCodeOwnerMapRepository;
import us.deloitteinnovation.aca.repository.IrsTransmissionDetailsRepository;
import us.deloitteinnovation.aca.repository.IrsTransmissionStatusRepository;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Created by tthakore on 3/28/2016.
 */

public class ErrorReportingObjectStore {

    final Properties configProps = new Properties();
    private static ErrorReportingObjectStore errorReportingObjectStore = null;
    private String currentFileName;
    private static Logger logger = LoggerFactory.getLogger(ErrorReportingObjectStore.class);
    private IrsTransmissionDetailsRepository irsTransmissionService;
    private IrsTransmissionStatusRepository irsTransmissionStatusService;
    private IrsErrorCodeOwnerMapRepository irsErrorCodeOwnerMapService;
    public Map<String, IrsTransmissionDetails> receiptTransmissionIDMap = new HashMap<>();
    public Map<String, IrsRecordDetails1095B> currentTransmissionRecordMap = new HashMap<>();
    private Integer submissionErrorCount = 0;
    private Integer recordErrorCount = 0;
    private Integer acceptedRecordCount = 0;
    private String outPutFolderPath = "";
    private String inPutFolderPath = "";
    private List<String> processedFileNameList = new ArrayList<>();
    private List<String> skippedFileNameList = new ArrayList<>();
    public static final String OUTPUT_FOLDER = "_OUTPUT_FOLDER";
    public static final String INPUT_FOLDER = "_INPUT_FOLDER";
    public Map<String, IrsTransmissionStatus> transmissionStatusesMap = new HashMap<>();
    public Map<String, IrsErrorCodeOwnerMapEntity> irsErrorCodeOwnerMapEntity = new HashMap<>();
    private String originalFileName = "";

    private ErrorReportingObjectStore() {
        try {
            final InputStream in = getClass().getClassLoader().getResourceAsStream(String.format("common.properties"));
            configProps.load(in);
        } catch (IOException ex) {
            throw new IllegalArgumentException("ErrorReportingObjectStore :Unable to read file common.properties");
        }

    }

    public static ErrorReportingObjectStore getInstance() {
        if (errorReportingObjectStore == null) {
            errorReportingObjectStore = new ErrorReportingObjectStore();
        }
        return errorReportingObjectStore;
    }

    public String getErrorFileFolder(String stateCode) throws IOException {
        String statepath;
        if (configProps.containsKey(stateCode.toUpperCase() + INPUT_FOLDER) && configProps.containsKey(stateCode.toUpperCase() + OUTPUT_FOLDER)) {
            statepath = configProps.getProperty(stateCode.toUpperCase() + INPUT_FOLDER);
            outPutFolderPath = configProps.getProperty(stateCode.toUpperCase() + OUTPUT_FOLDER);
        } else {
            throw new IllegalArgumentException("wrong state name entered");
        }

        inPutFolderPath = statepath;
        return statepath+"XML/";
    }


    public Resource[] getAvailableErrorXml(String stateCode) throws IOException {
        String stateFolder = getErrorFileFolder(stateCode);
        File folder = new File(stateFolder);
        ArrayList<Resource> fList = new ArrayList();
        File[] listOfFiles = folder.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".xml");
            }
        });
        for (int i = 0; i < listOfFiles.length; i++) {

            String receiptID = "";
            IrsTransmissionDetails irsTransmissionDetails;
            if (listOfFiles[i].isFile()) {
                String fileName = listOfFiles[i].getName();
                receiptID = getReceiptIdFromFileName(fileName);
                irsTransmissionDetails = (receiptID.length() > 0) ? getTransmissionInfoFromReceiptID(receiptID) : null;
                if (receiptID.length() > 0 && irsTransmissionDetails != null && irsTransmissionDetails.getTransmissionId() != null) {
                    Resource fsResource = new FileSystemResource(listOfFiles[i].getPath());
                    fList.add(fsResource);
                    receiptTransmissionIDMap.put(receiptID, irsTransmissionDetails);
                }
                else
                {
                    getSkippedFileNameList().add(fileName);
                }
            }
        }
        Resource[] fileList = new Resource[fList.size()];
        for (int i = 0; i < fList.size(); i++) {
            fileList[i] = (Resource) fList.get(i);
        }

        return fileList;
    }

    public String getReceiptIdFromFileName(String fileName) {
        String receiptID = "";
        try {
            receiptID = (fileName.split("_")[1]);//.toString().split("-")[2];
        } catch (Exception ex) {
            logger.error("Invalid FileName :" + fileName + " ", ex);
        }
        return receiptID;
    }


    public IrsTransmissionDetails getTransmissionInfoFromReceiptID(String receiptID) {
        IrsTransmissionDetails transmissionInfo = new IrsTransmissionDetails();
        try {
            List<IrsTransmissionDetails> irsTransmissionDetailses = irsTransmissionService.findByTransmissionReceiptId(receiptID);
            if (irsTransmissionDetailses.size() > 0) {
                IrsTransmissionDetails item = irsTransmissionService.findByTransmissionReceiptId(receiptID).get(0);
                transmissionInfo.setTransmissionId(item.getTransmissionId());
                transmissionInfo.setTransmissionFileName(item.getTransmissionFileName());
            }
        } catch (Exception ex) {
            logger.error("unable to find transmission id for receiptID:" + receiptID + " File will be ignored.", ex);
        }

        return transmissionInfo;
    }

    public List<String> getProcessedFileNameList() {
        return processedFileNameList;
    }

    public void setProcessedFileNameList(List<String> processedFileNameList) {
        this.processedFileNameList = processedFileNameList;
    }


    /**
     * all the getters and setters will start from here
     **/
    public IrsTransmissionDetailsRepository getIrsTransmissionService() {
        return irsTransmissionService;
    }

    public void setIrsTransmissionService(IrsTransmissionDetailsRepository irsTransmissionService) {
        this.irsTransmissionService = irsTransmissionService;

    }

    public Integer getCurrentTransmissionID() {
        String receiptID = getReceiptIdFromFileName(getCurrentFileName());
        return Integer.valueOf(receiptTransmissionIDMap.get(receiptID).getTransmissionId());
    }

    public String getCurrentFileName() {
        return currentFileName;
    }

    public void setCurrentFileName(String currentFileName) {
        this.currentFileName = currentFileName;
    }

    public Properties getConfigProps() {
        return configProps;
    }

    public Integer getAcceptedRecordCount() {
        return acceptedRecordCount;
    }

    public void setAcceptedRecordCount(Integer acceptedRecordCount) {
        this.acceptedRecordCount = acceptedRecordCount;
    }

    public Integer getRecordErrorCount() {
        return recordErrorCount;
    }

    public void setRecordErrorCount(Integer recordErrorCount) {
        this.recordErrorCount = recordErrorCount;
    }

    public Integer getSubmissionErrorCount() {
        return submissionErrorCount;
    }

    public void setSubmissionErrorCount(Integer submissionErrorCount) {
        this.submissionErrorCount = submissionErrorCount;
    }

    public String getOutPutFolderPath() {
        return outPutFolderPath;
    }

    public String getInputFolderPath() {
        return inPutFolderPath;
    }

    public void setOutPutFolderPath(String outPutFolderKey) {
        this.outPutFolderPath = outPutFolderKey;
    }

    public IrsTransmissionStatusRepository getIrsTransmissionStatusService() {
        return irsTransmissionStatusService;
    }

    public void setIrsTransmissionStatusService(IrsTransmissionStatusRepository irsTransmissionStatusService) {
        this.irsTransmissionStatusService = irsTransmissionStatusService;
        List<IrsTransmissionStatus> transmissionStatuses = irsTransmissionStatusService.getTransmissionStatus("%ERS%");
        for (IrsTransmissionStatus statusObj : transmissionStatuses) {
            transmissionStatusesMap.put(statusObj.getId().getStatusCD(), statusObj);
        }
    }

    public IrsErrorCodeOwnerMapRepository getIrsErrorCodeOwnerMapService() {
        return irsErrorCodeOwnerMapService;
    }

    public void setIrsErrorCodeOwnerMapService(IrsErrorCodeOwnerMapRepository irsErrorCodeOwnerMapService) {
        this.irsErrorCodeOwnerMapService = irsErrorCodeOwnerMapService;
        List<IrsErrorCodeOwnerMapEntity> irsErrorCodeOwnerMapEntities = irsErrorCodeOwnerMapService.getTransmissionStatus();
        for (IrsErrorCodeOwnerMapEntity statusObj : irsErrorCodeOwnerMapEntities) {
            irsErrorCodeOwnerMapEntity.put(statusObj.getErrorCode(), statusObj);
        }
    }

    public List<String> getSkippedFileNameList() {
        return skippedFileNameList;
    }

    public void setSkippedFileNameList(List<String> skippedFileNameList) {
        this.skippedFileNameList = skippedFileNameList;
    }
    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }


}
