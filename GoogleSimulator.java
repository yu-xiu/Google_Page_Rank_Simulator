/**import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import javafx.util.Pair;

import javax.swing.*;
*/
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

import java.awt.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

/**
 * A GoogleSimulator class which contains the main method and store each web page information crawled from web crawler
 * into an array. Display a1, a2, a3, a4 for user to enter weights. Sort all web page info and display the results
 */
public class GoogleSimulator {

    static final int totalWebPage = 100;
    static final int showWebPage = 30;
    // An Array to store each web page information that the web crawler visited
    //static WebPageInformation[] allWebPageInfo = new WebPageInformation[totalWebPage];
    //static int count = 0;
    static ArrayList<WebPageInformation> allWebPageInfo = new ArrayList();

    /**
     * This function declares the depth and the maximum pages that the crawler could crawl
     * Time complexity is O(1)
     * @return true
     * @throws Exception
     */
     Boolean crawlWebPages(String keyword) throws Exception {
        //String crawlStorageFolder = "/Users/yuxiu/Desktop/tmp";
        String crawlStorageFolder = ".";
        int numberOfCrawlers = 1;
        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder(crawlStorageFolder);
        // Set depth and the maximum pages for crawler
        config.setMaxDepthOfCrawling(2);
        config.setMaxPagesToFetch(totalWebPage);
        config.setIncludeHttpsPages(true);


        /*
         * Instantiate the controller for this crawl.
         */
        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        robotstxtConfig.setEnabled(false);
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

        /*
         * For each crawl, you need to add some seed urls. These are the first
         * URLs that are fetched and then the crawler starts following links
         * which are found in these pages
         */
        //controller.addSeed("https://www.ics.uci.edu/~lopes/");
        //controller.addSeed("https://www.ics.uci.edu/~welling/");
        controller.addSeed("https://www.bing.com/search?q=" + keyword);

        /*
         * Start the crawl. This is a blocking operation, meaning that your code
         * will reach the line after this only when crawling is finished.
         */
       controller.start(MyCrawler.class, numberOfCrawlers);
        return true;
    }


    /*
     * Main Function
     * Crawl web pages
     * Display a1, a2, a3, a4 for user to enter weights
     */
    public static void main(String[] args) throws Exception {
        GoogleSimulator sim = new GoogleSimulator();


        //System.out.println("Number of Web pages is " + allWebPageInfo.size());

        Hashtable<String, KeywordInformation> keywordInfo = new Hashtable<String, KeywordInformation>();

        // Display a1 a2 a3 a4 for client to change
        Scanner sc = new Scanner(System.in);
        System.out.println("-------------------------------------------------------------------------------------");
        System.out.print("Enter a number for a1: ");
        double a1 = sc.nextDouble();

        System.out.print("Enter a number for a2: ");
        double a2 = sc.nextDouble();

        System.out.print("Enter a number for a3: ");
        double a3 = sc.nextDouble();

        System.out.print("Enter a number for a4: ");
        double a4 = sc.nextDouble();

        sc.nextLine();

        // A while loop that allowed user to put another keyword after entering four weights
        while (true) {
            System.out.println("-------------------------------------------------------------------------------------");
            System.out.print("Enter a keyword: ");
            String keyword = sc.nextLine();
            // Features of Hash table
            // Enter empty key
            if (keyword.equals("")) { break; }
            // If the keyword is already exist, the count + 1, otherwise put 1
            if (keywordInfo.containsKey(keyword)) {
                keywordInfo.get(keyword).count++;
            } else {
                KeywordInformation k = new KeywordInformation(keyword, 1);
                keywordInfo.put(keyword, k);
            }

            allWebPageInfo.clear();
            // crawl web pages
            sim.crawlWebPages(keyword);

/*             for (int i = 0; i < allWebPageInfo.size(); i++) {
             System.out.println("PageRank " + (i + 1) + " " +
             allWebPageInfo.get(allWebPageInfo.size() - i - 1).toString());
             }
*/
            // Save crawled web pages into keyword information object
            KeywordInformation k = keywordInfo.get(keyword);
            // clear all existed web pages
            k.crawledWebPages.clear();
            for (int i = 0; i < allWebPageInfo.size(); i++) {
                k.crawledWebPages.add(allWebPageInfo.get(i));
            }


            //Calculate score for every page

            for (int i = 0; i < allWebPageInfo.size(); i++) {
                allWebPageInfo.get(i).CalculateScore(keyword, a1, a2, a3, a4);
            }

            // sort web page by score using heap sort
            // HeapSort sort = new HeapSort();
            // sort.Heapsort(allWebPageInfo);

            // sort web page by score using quick sort
            QuickSort sort = new QuickSort();

            // convert allWebPageInfo into array
            WebPageInformation[] webpages = allWebPageInfo.toArray(new WebPageInformation[0]);

            sort.quickSort(webpages,0,webpages.length - 1);

            // Printing out sorted web pages with required information and PageRank
            for (int i = 0; i < showWebPage && i < webpages.length; i++) {
                System.out.println("PageRank " + (i + 1) + " " +
                        webpages[webpages.length - i - 1].toString());

            }

            System.out.print("Test Process BST? Y/N: ");
            String yesOrNo = sc.nextLine();

            if (yesOrNo.startsWith("Y")) {
                testProcessBST(webpages);
            }

        }

        // hashTable -> arrayList -> array -> heapsort
        ArrayList<KeywordInformation> array = new ArrayList<KeywordInformation>();
        // Get the set of pairs of keyword and key and add them to array
        for (String keyword: keywordInfo.keySet()) {
            array.add(keywordInfo.get(keyword));
        }
        // Creating an object and sort array using heapsort
        // Converting arrayList to array
        KeywordInformation[] array1 = array.toArray(new KeywordInformation[0]);
        HeapSort sort1 = new HeapSort();
        sort1.Heapsort(array1);

        // Printing out top 10 keywords and their counts
        for (int i = 0; i < 10 && i < array1.length; i++) {
            System.out.println(array1[array1.length - i - 1].toString());
        }

        KeywordInformation mostPopularKeyWord = array1[array1.length - 1];
        System.out.println("The top first keyword is " + mostPopularKeyWord.keyword +
                "\nThe results are sorted by company names");


        // Use bucket sort to sort the top first keyword's companies' names
        BucketSort sortNames = new BucketSort();
        sortNames.buckSort(mostPopularKeyWord.crawledWebPages);
        System.out.println("-------------------------------------------------------------------------------------");
        for (int i = 0; i < mostPopularKeyWord.crawledWebPages.size(); i++) {
            System.out.println(mostPopularKeyWord.crawledWebPages.get(i));
        }

        // Create a tree and insert nodes into the tree
        BinarySearchTree bst = new BinarySearchTree();



/*
        ////////////GUI
        // Create main frame in which program will run
        JFrame frmWindow = new JFrame("Google Simulator ");// This will be printed in the the title bar
        frmWindow.setSize(300, 200);
        frmWindow.setLocationRelativeTo(null);//Force new object JFrame to be centered on the computer screen
        frmWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//click little x to close JFrame

        //Set up layout which will control placement of buttons
        FlowLayout layout = new FlowLayout(FlowLayout.CENTER, 5, 20);//Set gaps
        // Sets the layout manager of the JFrame to the layout manager object, layout
        frmWindow.setLayout(layout);

        // Create a label, a text field and a button
        JLabel lb = new JLabel("Enter keyword: "); // Create a JLabel object called lb and place a text
        JTextField txtArea =  new JTextField(10);// Create the text input area
        JButton btn = new JButton("Press Here to See Results");

        // Place controls into the frame
        frmWindow.add(lb);
        frmWindow.add(txtArea);
        frmWindow.add(btn);

        // Display frame to the user
        frmWindow.setVisible(true);
*/


    }

    /**
     *
     * @param webpages
     */
    static void testProcessBST (WebPageInformation[] webpages) {

        BinarySearchTree.Tree processBST = new BinarySearchTree.Tree();

        for (int i = 0; i < webpages.length; i ++) {
            BinarySearchTree.Node n = new BinarySearchTree.Node(webpages.length - i, webpages[i]);
            BinarySearchTree.treeInsert(processBST, n);
        }
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("-------------------------------------------------------------------------------------");

            System.out.println("Options: 1. Search Page Rank; 2. Insert URL; 3. Delete URL; 4. Show tree; 0. Exit;");

            String input = sc.nextLine();
            // If user chooses 0
            if (input.compareTo("0") == 0) {
                break;
            }
            // If user chooses 1, search a node based on rank
            else if (input.compareTo("1") == 0) {
                System.out.println("Enter a Page Rank: ");
                int number = sc.nextInt();
                sc.nextLine();
                BinarySearchTree.Node node = BinarySearchTree.treeSearch(processBST.root, number);
                System.out.println(node);
            }
            // If user chooses 2, insert a node based rank/total score in tree
            else if (input.compareTo("2") == 0) {
                System.out.println("Enter a Page Rank: ");
                int number = sc.nextInt();
                sc.nextLine();
                System.out.println("Enter a URL: ");
                String url = sc.nextLine();
                WebPageInformation info = new WebPageInformation();
                info.setUrl(url);
                BinarySearchTree.Node node = new BinarySearchTree.Node(number, info);
                BinarySearchTree.treeInsert(processBST, node );
            }
            // If user chooses 3; delete a node from tree
            else if (input.compareTo("3") == 0) {
                System.out.println("Enter a Page Rank: ");
                int number = sc.nextInt();
                sc.nextLine();
                BinarySearchTree.Node node = BinarySearchTree.treeSearch(processBST.root, number);
                BinarySearchTree.treeDelete(processBST, node);
            }
            // If user chooses 4; use inorder traversal to sort all web pages information
            else if (input.compareTo("4") == 0) {
                BinarySearchTree.inorderTreeWalk(processBST.root);
            }
        }

    }
}
