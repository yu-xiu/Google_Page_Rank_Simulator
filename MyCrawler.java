
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * This class extends WebCrawler class which is a developed simple Web Crawler
 * It contains shouldVisit and visit methods which give us a chance to save the information form each web page that
 * we might use later
 */
public class MyCrawler extends WebCrawler {
    // Random variable object
    static Random rand = new Random();

    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg"
            + "|png|mp3|mp4|zip|gz))$");

    /**
     * This method receives two parameters. The first parameter is the page
     * in which we have discovered this new url and the second parameter is
     * the new url. You should implement this function to specify whether
     * the given url should be crawled or not (based on your crawling logic).
     * In this example, we are instructing the crawler to ignore urls that
     * have css, js, git, ... extensions and to only accept urls that start
     * with "https://www.ics.uci.edu/". In this case, we didn't need the
     * referringPage parameter to make the decision.
     */
    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase();
        return !FILTERS.matcher(href).matches()
                && !href.startsWith("https://www.bing.com/");
    }

    /**
     * This function is called when a page is fetched and ready
     * to be processed by your program.
     * Time complexity is O(1)
     */
    @Override
    public void visit(Page page) {
        String url = page.getWebURL().getURL();
        System.out.println("URL: " + url);

        if (page.getParseData() instanceof HtmlParseData) {
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            String text = htmlParseData.getText();
            String html = htmlParseData.getHtml();
            Set<WebURL> links = htmlParseData.getOutgoingUrls();

            System.out.println("Text length: " + text.length());
            System.out.println("Html length: " + html.length());
            System.out.println("Number of outgoing links: " + links.size());

            if (GoogleSimulator.allWebPageInfo.size() < GoogleSimulator.showWebPage) {
                // Every time we visit a web page, store the page's information into info
                WebPageInformation info = new WebPageInformation();
                info.setUrl(url);// usage of setter
                info.setHtml(html);
                info.setOutGoingLink(links.size());
                info.setText(text);

                // Randomly assign value for each feature
                info.setAds(rand.nextInt(100) + 1);
                info.setAge(rand.nextInt(50) + 1);
                info.setRelevance(rand.nextInt(80) + 1);

                // Assign index for each web page
                info.setIndex(GoogleSimulator.allWebPageInfo.size());

                // New web page info
                GoogleSimulator.allWebPageInfo.add(info);
//            GoogleSimulator.allWebPageInfo[GoogleSimulator.count] = info;
//            GoogleSimulator.count++;
            }

        }

    }

}