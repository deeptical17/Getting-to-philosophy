/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hd.wiki.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

/**
 *
 * @author Deepti
 */
@Service
public class GettingToPhilosophy {

    private static final String PHILOSOPHY = "philosophy";
    private static final String baseEnglishUrl = "https://en.wikipedia.org/wiki/";
    private static final String wikiActionQuery = "?action=raw";
    private static List<String> linkPath = new ArrayList<String>();

    public Map<String, String> getPathFor(String title) throws IOException,
            ClassNotFoundException, SQLException {
        Map<String, String> map = new HashMap<>();
        if (!title.equalsIgnoreCase(PHILOSOPHY)) {

            linkPath.add(title);
            findAndProcessNextLink(title, map);
        }

        return map;
    }

    private void findAndProcessNextLink(String title, Map map) throws IOException {

        String content = fetchWikiText(title);
        if (content == null) {
            // System.err.println("Malformated URL ,most probably");

        } else {
            char openBracket1 = '(';
            char openBracket2 = '{';
            char openBracket3 = '<';
            char openBracket4 = '[';

            char closedBracket1 = ')';
            char closedBracket2 = '}';
            char closedBracket3 = '>';
            char closedBracket4 = ']';

            // Citation links end with </ref> for e.g <ref>[[iCracked]]</ref>
            int beginIndex = -1;
            int endIndex = -1;

            int open1Counter = 0;
            int open2Counter = 0;
            int open3Counter = 0;
            int open4Counter = 0;
            char c;

            for (int i = 0; i < content.length(); i++) {
                c = content.charAt(i);
                if (c == openBracket1) {
                    open1Counter++;
                }
                if (c == openBracket2) {
                    open2Counter++;
                }
                if (c == openBracket3) {
                    open3Counter++;
                }
                if (c == openBracket4) {
                    open4Counter++;
                }
                // decrement the bracket if it is closed
                if (c == closedBracket1) {
                    open1Counter--;
                }
                if (c == closedBracket2) {
                    open2Counter--;
                }
                if (c == closedBracket3) {
                    open3Counter--;
                }
                if (c == closedBracket4) {
                    open4Counter--;
                }

                if (open1Counter == 0 && open2Counter == 0 && open3Counter == 0
                        && open4Counter == 2 && c == openBracket4) {
                    beginIndex = i + 1;
                }

                String next;

                if (open1Counter == 0 && open2Counter == 0 && open3Counter == 0
                        && open4Counter == 0 && c == closedBracket4
                        && beginIndex != -1) {
                    boolean isCitation = false;

                    if (!isCitation) {
                        endIndex = i - 2;
                        next = content.substring(beginIndex, endIndex + 1);
                        if (!next.contains(":")) {
                            int separator = next.indexOf("|");
                            int separator2 = next.indexOf("#");

                            if (separator2 != -1 && separator2 < separator) {
                                separator = separator2;
                            }

                            next = next.replace(" ", "_");
                            if (separator != -1) {
                                next = next.substring(0, separator);
                            }

                            map.put(next, "https://en.wikipedia.org/wiki/" + next);
                            if (!(next.equalsIgnoreCase(PHILOSOPHY))
                                    && linkPath.contains(next)) {
                            }

                            linkPath.add(next);
                            if (isPhilosophy(next)) {
                                break;
                            } // YEYY.. Done!!
                            else {
                                findAndProcessNextLink(next, map);
                                break;
                            }
                        } else {
                            endIndex = beginIndex = -1;
                        }
                    }
                }
            }
        }

    }
    private final String USER_AGENT = "Mozilla/5.0";
    private String fetchWikiText(String title) throws MalformedURLException, IOException {

        String url = "https://en.wikipedia.org/wiki/" + title + "?action=raw";

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }

    private boolean isPhilosophy(String title) {
        return title.equalsIgnoreCase(PHILOSOPHY);
    }
}
