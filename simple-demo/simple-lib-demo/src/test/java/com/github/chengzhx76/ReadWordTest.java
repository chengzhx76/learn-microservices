package com.github.chengzhx76;

import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @Description
 * @Author admin
 * @Date 2020/11/19 16:35
 * @Version 3.0
 */
public class ReadWordTest {

    static final Pattern REX_NUMBER = Pattern.compile("\\d+");
    static final Pattern REX_LETTER = Pattern.compile("[a-zA-Z]");
    static final Pattern REX_CHINESE = Pattern.compile("[^\\x00-\\xff]");

    public static void main(String[] args) throws IOException {

        String fileName = "D:\\b.docx";

        InputStream is = Files.newInputStream(Paths.get(fileName));
        try (XWPFDocument doc = new XWPFDocument(is)) {
            List<XWPFParagraph> paragraphs = doc.getParagraphs();
            String question = "";
            String number = "";
            List<String> options = new ArrayList<>();
            String answer = "";

            boolean isEnd = false;

            for (XWPFParagraph paragraph : paragraphs) {
                String line = paragraph.getText();
                if (StrUtil.isBlank(line)) {
                    continue;
                }
                line = StrUtil.trimStart(line);
                String firstLetter = line.substring(0, 1);
                if (ReUtil.isMatch(REX_NUMBER, firstLetter)) {

                    question = line;
                    number = ReUtil.getGroup0(REX_NUMBER, question);
                } else if ((ReUtil.isMatch(REX_LETTER, firstLetter)
                        || ReUtil.isMatch(REX_CHINESE, firstLetter))
                        && !StrUtil.equals(firstLetter, "答")) {

                    String[] answers = line.split("[a-zA-Z]");
                    for (int i = 0; i <answers.length ; i++) {
                        if (!StrUtil.isBlank(answers[i])) {
                            String _answer = answers[i];
                            String prefix = "";
                            if (i == 0) {
                                prefix = "A";
                            } else if (i == 1) {
                                prefix = "B";
                            } else if (i == 2) {
                                prefix = "C";
                            } else if (i == 3) {
                                prefix = "D";
                            }
                            _answer = StrUtil.trim(_answer);
                            options.add(prefix + _answer);
                        }
                    }

                } else if (StrUtil.equals(firstLetter, "答")) {
                    isEnd = true;
                    answer = ReUtil.get(REX_LETTER, line, 0);
                } else {
                    System.err.println("未知 line[" + number + "] " + line);
                }

                if (isEnd) {


                    System.out.println("编号：" + number);
                    System.out.println("题目：" + question);
                    System.out.println("选项：len[" + options.size() + "]" + options);
                    System.out.println("答案：" + answer);
                    System.out.println("-------------------");

                    if (options.size() > 4) {
                        System.err.println("编号：" + number);
                        return;
                    }
                    isEnd = false;
                    question = "";
                    number = "";
                    options = new ArrayList<>();
                    answer = "";
                }
            }

        }

    }



}
