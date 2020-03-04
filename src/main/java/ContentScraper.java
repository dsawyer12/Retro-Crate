import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;

public class ContentScraper {
    public static void retrieveConsoleTitles() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("./files/ConsoleTitles", true));
        String url = "https://github.com/libretro/libretro-thumbnails";
        Document document = Jsoup.connect(url).get();
        Element tableBody;
        Elements title;
        String value;

        for (int i = 2; i <= 92; i++) {
            String selector = "#js-repo-pjax-container > div.container-lg.clearfix.new-discussion-timeline.px-3 > div > div.file-wrap > table > tbody > tr:nth-child(" + i + ") > td.content > span";
            tableBody = document.select(selector).get(0);
            title = tableBody.getElementsByTag("a");
            value = title.attr("title");
            if (value != null) {
                System.out.println(value);
                writer.append("\n");
                writer.append(value);
            }
        }
        writer.close();
    }

    public static void retrieveConsoleLinks() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("./files/ConsoleTitleLinks", true));
        String url = "https://github.com/libretro/libretro-thumbnails";
        Document document = Jsoup.connect(url).get();
        String selector;
        Element tableBody;
        Elements link;
        String value;

        for (int i = 2; i <= 92; i++) {
            selector = "#js-repo-pjax-container > div.container-lg.clearfix.new-discussion-timeline.px-3 > div > div.file-wrap > table > tbody > tr:nth-child(" + i + ") > td.content > span";
            tableBody = document.select(selector).get(0);
            link = tableBody.getElementsByTag("a");
            value = link.attr("href");
            if (value != null) {
                System.out.println(value);
                writer.append("\n");
                writer.append(value);
            }
        }
        writer.close();
    }

    public static void retrieveConsoleBoxArtLink () throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("./files/ConsoleTitleLinks"));
        BufferedWriter writer = new BufferedWriter(new FileWriter("./files/ConsoleBoxArtLinks"));
        String url = "http://thumbnailpacks.libretro.com";
        Document document = Jsoup.connect(url).get();
        String selector;
        Element item;
        Elements link;
        String value;

        for (int i = 2; i <= 105; i++) {
            selector = "body > pre > a:nth-child(" + i + ")";
            item = document.select(selector).get(0);
            link = item.getElementsByTag("a");
            value = link.attr("href");
            if (value != null) {
                System.out.println(value);
//                writer.append("\n");
//                writer.append(value);
            }
        }
        writer.close();
    }

    /********** SCRAPER FROM LAUNCHBOX **********/

    public static ArrayList<Console> getConsoleData() throws IOException {
        ArrayList<Console> consoles = new ArrayList<>();
        String URL = "https://gamesdb.launchbox-app.com/platforms/index/1";
        Document document = Jsoup.connect(URL).get();

        Elements childElements = document.getElementsByClass("list-item");

        for (Element element : childElements) {
            String href = element.attr("abs:href");
            String title = element.select("div.row").select("div.col-sm-10").select("h3").text();
            String description = element.select("div.row").select("div.col-sm-10").select("p").text();
            String img = element.select("div.row").select("div.col-sm-2").select("img.img-responsive").attr("src");
            if (!img.isEmpty()) {
                URL imgUrl = new URL(img);
                try {
                    BufferedImage image = ImageIO.read(imgUrl);
                    if (title.contains("/"))
                        title = title.replace("/", "-");
                    String imgPath = "files/ConsoleImages/" + title + ".png";
                    File file = new File(imgPath);
                    ImageIO.write(image, "png", file);
                    Console console = new Console(href, title, description, imgPath);
                    consoles.add(console);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                Console console = new Console(href, title, description, null);
                consoles.add(console);
            }
        }

        return consoles;
    }
}










