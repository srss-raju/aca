package us.deloitteinnovation.aca.batch.mapper;

import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

/**
 */
public class DefaultLineMapper<T> implements LineMapper<T>, InitializingBean {
    @Autowired
    private LineTokenizer tokenizer;

    @Autowired
    private FieldSetMapper<T> fieldSetMapper;

    /**
     * @param line
     * @param lineNumber
     * @return
     * @throws Exception
     */
    public T mapLine(String line, int lineNumber) throws Exception {
        return fieldSetMapper.mapFieldSet(tokenizer.tokenize(line), lineNumber);
    }

    public void afterPropertiesSet() {
        Assert.notNull(tokenizer, "The LineTokenizer must be set");
        Assert.notNull(fieldSetMapper, "The FieldSetMapper must be set");
    }

    public LineTokenizer getTokenizer() {
        return tokenizer;
    }

    public void setTokenizer(LineTokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    public FieldSetMapper<T> getFieldSetMapper() {
        return fieldSetMapper;
    }

    public void setFieldSetMapper(FieldSetMapper<T> fieldSetMapper) {
        this.fieldSetMapper = fieldSetMapper;
    }
}