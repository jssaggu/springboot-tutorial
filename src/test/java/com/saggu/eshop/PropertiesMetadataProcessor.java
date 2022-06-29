package com.saggu.eshop;


import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.metadata.ConfigurationMetadata;
import org.springframework.boot.configurationprocessor.metadata.ItemHint;
import org.springframework.boot.configurationprocessor.metadata.ItemMetadata;
import org.springframework.boot.configurationprocessor.metadata.JsonMarshaller;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * {@code PropertiesMetadataProcessor} is an example of processing
 * spring configuration metatdata and turn it into an HTML file.
 * <p>
 */
@ExtendWith(SpringExtension.class)
public class PropertiesMetadataProcessor {

    @Value(value = "classpath:spring-configuration-metadata.json")
    private Resource metaDataJson;

    private List<Map<String, Object>> convertHintsToKeyValue(
            List<ItemHint> hints) {
        List<Map<String, Object>> hintlist = new LinkedList<>();

        for (ItemHint hint : hints) {
            Map<String, Object> hintMap = new HashMap<>();
            hintlist.add(hintMap);

            hintMap.put("name", hint.getName());

            List<Map<String, String>> valueList = new LinkedList<>();
            for (ItemHint.ValueHint value : hint.getValues()) {
                Map<String, String> valueMap = new HashMap<>();
                valueList.add(valueMap);
                if (value.getValue() != null) {
                    valueMap.put("value", value.getValue().toString());
                }
                if (value.getDescription() != null) {
                    valueMap.put("description", value.getDescription());
                }
            }

            if (valueList.size() > 0) {
                hintMap.put("values", valueList);
            }

            List<Map<String, Object>> providerList = new LinkedList<>();
            for (ItemHint.ValueProvider provider : hint.getProviders()) {
                Map<String, Object> providerMap = new HashMap<>();
                providerList.add(providerMap);
                if (provider.getName() != null) {
                    providerMap.put("name", provider.getName());
                }

                if (provider.getParameters() != null &&
                        !provider.getParameters().isEmpty()) {
                    Map<String, String> parameters = new HashMap<>();
                    provider.getParameters().entrySet().forEach(
                            e -> parameters.put(e.getKey(),
                                    e.getValue().toString()));
                    providerMap.put("parameters", parameters);
                }
            }

            if (providerList.size() > 0) {
                hintMap.put("providers", providerList);
            }
        }

        return hintlist;
    }

    @Test
    public void testMarshalling() throws Exception {
        assertNotNull(metaDataJson);
        metaDataJson.getInputStream();

        ConfigurationMetadata metadata =
                new JsonMarshaller().read(metaDataJson.getInputStream());

        convertToHtml(metadata);
    }

    private void convertToHtml(
            ConfigurationMetadata metadata) throws IOException, TemplateException {
        Configuration cfg = new Configuration(new Version(2, 3, 27));

        // template is loaded from 'templates' folder under resources
        cfg.setClassForTemplateLoading(
                PropertiesMetadataProcessor.class,
                "/templates");
        cfg.setDefaultEncoding("UTF-8");
        cfg.setLocale(Locale.US);
        cfg.setTemplateExceptionHandler(
                TemplateExceptionHandler.RETHROW_HANDLER);

        Map<String, Object> input = new HashMap<String, Object>();
        input.put("title", "Spring Configuration for MAPS-Core");
        input.put("metadata", metadata);
        input.put("GROUP", ItemMetadata.ItemType.GROUP);
        input.put("PROPERTY", ItemMetadata.ItemType.PROPERTY);

        Template template = cfg.getTemplate("metadata.ftl");

        // write output to the console
        Writer consoleWriter = new OutputStreamWriter(System.out);
        template.process(input, consoleWriter);

        // write output into a file
        Writer fileWriter = new FileWriter(new File("metadata.html"));
        try {
            template.process(input, fileWriter);
        } finally {
            fileWriter.close();
        }
    }
}