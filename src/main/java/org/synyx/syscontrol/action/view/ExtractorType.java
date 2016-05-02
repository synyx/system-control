package org.synyx.syscontrol.action.view;

import org.springframework.beans.BeanUtils;
import org.springframework.web.client.ResponseExtractor;
import org.synyx.syscontrol.extractor.JsonPathResponseExtractor;
import org.synyx.syscontrol.execution.ExecutionResult;
import org.synyx.syscontrol.extractor.Extractors;

import java.util.Map;

/**
 * @author Marc Kannegiesser - kannegiesser@synyx.de
 */
public enum ExtractorType {
    STATUS {
        @Override
        public ResponseExtractor<ExecutionResult> createExtractor(Map<String, String> extractorConfiguration) {
            return Extractors.DEFAULT_EXTRACTOR;
        }
    }, 
    FULL_BODY {
        @Override
        public ResponseExtractor<ExecutionResult> createExtractor(Map<String, String> extractorConfiguration) {
            return Extractors.FULL_BODY_EXTRACTOR;
        }
    }, 
    CUSTOM {
        @Override
        public ResponseExtractor<ExecutionResult> createExtractor(Map<String, String> extractorConfiguration) {
            String clazz = extractorConfiguration.get("class");
            try {
                ResponseExtractor extractor = BeanUtils.instantiateClass(Class.forName(clazz), ResponseExtractor.class);
                @SuppressWarnings("unchecked")
                ResponseExtractor<ExecutionResult> result =  (ResponseExtractor<ExecutionResult>) extractor;
                return result;
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Could not create extractor from class " + clazz + ": " + e.getMessage(),e);
            }
        }
    }, 
    JSON_PATH {
        @Override
        public ResponseExtractor<ExecutionResult> createExtractor(Map<String, String> extractorConfiguration) {
            return new JsonPathResponseExtractor(extractorConfiguration);
        }
    };

    public abstract ResponseExtractor<ExecutionResult> createExtractor(Map<String, String> extractorConfiguration);
}
