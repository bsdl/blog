package com.bsdl.util;

import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TableBlock;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.ext.heading.anchor.HeadingAnchorExtension;
import org.commonmark.node.Link;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.AttributeProvider;
import org.commonmark.renderer.html.HtmlRenderer;

import java.util.*;

/**
 * @program: blog
 * @create: 2020-07-25 14:44
 **/
public class MarkdownUtils {

    /**
     * markdown格式转换成HTML格式
     * @param markdown 博客内容
     * @return
     */
    public static String markdownToHtml(String markdown) {
        Parser parser = Parser.builder().build();
        Node document = parser.parse(markdown);
        HtmlRenderer htmlRenderer = HtmlRenderer.builder().build();
        return htmlRenderer.render(document);
    }

    /**
     * 增加扩展[标题锚点，表格生成]
     * Markdown转换成HTML
     * @param markdown
     * @return
     */
    public static String markdownToHtmlExtensions(String markdown) {
        // h
        Set<Extension> headingAnchorExtensions = Collections.singleton(HeadingAnchorExtension.create());
        List<Extension> tableExtensions = Collections.singletonList(TablesExtension.create());
        Parser parser = Parser.builder().extensions(tableExtensions).build();
        Node document = parser.parse(markdown);
        HtmlRenderer htmlRenderer = HtmlRenderer.builder()
                .extensions(headingAnchorExtensions)
                .extensions(tableExtensions)
                .attributeProviderFactory(attributeProviderContext -> new CustomAttributeProvider()).build();
        return htmlRenderer.render(document);
    }

    /**
     * 处理标签的属性
     */
    public static class CustomAttributeProvider implements AttributeProvider {

        @Override
        public void setAttributes(Node node, String s, Map<String, String> attributes) {
            // 如果是链接，点开新打开一个标签
            if (node instanceof Link) {
                attributes.put("target", "_blank");
            }
            if (node instanceof TableBlock) {
                attributes.put("class", "ui celled table");
            }
        }
    }

    public static void main(String[] args) {
        String table = "| hello | hi   | 哈哈哈   |\n" +
                "| ----- | ---- | ----- |\n" +
                "| 斯维尔多  | 士大夫  | f啊    |\n" +
                "| 阿什顿发  | 非固定杆 | 撒阿什顿发 |\n" +
                "\n";
        String a = "[bing](http://cn.bing.com)";
        System.out.println(markdownToHtmlExtensions(a));
    }
}
