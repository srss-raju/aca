DELETE FROM FILER_COVERAGE_SOURCE
WHERE SOURCE_CD = 'INFSSICE' AND SOURCE_UNIQUE_ID = '123456789'
DELETE FROM FILER_DEMOGRAPHICS
WHERE SOURCE_CD = 'INFSSICE' AND SOURCE_UNIQUE_ID = '123456789' AND FILER_DEMOGRAPHICS.TAX_YEAR = 2017