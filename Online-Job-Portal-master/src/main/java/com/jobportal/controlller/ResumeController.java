package com.jobportal.controlller;

import opennlp.tools.tokenize.SimpleTokenizer;
import opennlp.tools.tokenize.Tokenizer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/resume")
public class ResumeController {

    @PostMapping("/upload")
    public List<String> extractKeywords(@RequestParam("file") MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        String content = new String(inputStream.readAllBytes());
        return extractKeywordsFromContent(content);
    }

    private List<String> extractKeywordsFromContent(String content) {
        Tokenizer tokenizer = SimpleTokenizer.INSTANCE;
        String[] tokens = tokenizer.tokenize(content);
        List<String> stopwords = Arrays.asList("a", "an", "the", "in", "on", "at", "to", "for", "of", "and", "with", "is", "are", "was", "were", "am", "has", "have", "had");
        List<String> keywords = new ArrayList<>();

        for (String token : tokens) {
            if (!stopwords.contains(token.toLowerCase())) {
                keywords.add(token.toLowerCase());
            }
        }

        return keywords;
    }
}
