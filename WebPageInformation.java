/**
 * This class stores the information from each web page crawled by the web crawler
 * Get and set the information collected from the web crawler, which are the url, text, html, and out going links toward
 * the page.
 * In CalculateScore function, it caculates a key word frequency by finding the string occurrence in the text
 * of a web page, then caculate the unique score of each web page by plus the results of a1 * frequency, a2 * age, a3 *
 * relevance, and a4 * ads.
 * The compareTo function compares the scores caculated based on two web pages.
 * Return a string representation of the comparing results
 */
public class WebPageInformation implements Comparable<WebPageInformation> {
    // Member variables
    String url;
    String text;
    String html;
    int outGoingLink;
    int frequency;
    int age;
    int relevance;
    int ads;
    double score;
    int index;

    /**
     * Time complexity is O(1)
     * @return url of this web page
     */
    public String getUrl() {
        return url;
    }

    /**
     * Time complexity is O(1)
     * @return text of this web page
     */

    public String getText() {
        return text;
    }

    /**
     * Time complexity is O(1)
     * @return html of this web page
     */
    public String getHtml() {
        return html;
    }

    /**
     * Time complexity is O(1)
     * @return outgoing link of this web page
     */
    public int getOutGoingLink() {
        return outGoingLink;
    }

    /**
     * Set a new url for this web page
     * Time complexity is O(1)
     * @param newUrl
     */
    void setUrl(String newUrl) {
        url = newUrl;
    }

    /**
     * Set a new text for this web page
     * Time complexity is O(1)
     * @param newText
     */
    void setText(String newText) {
        text = newText;
    }

    /**
     * Set a new html for this web page
     * Time complexity is O(1)
     * @param newHtml
     */
    void setHtml(String newHtml) {
        html = newHtml;
    }

    /**
     * set a new out going link to this web page
     * Time complexity is O(1)
     * @param newOutGoingLink
     */
    void setOutGoingLink(int newOutGoingLink) {
        outGoingLink = newOutGoingLink;
    }

    /**
     * Attribute of web crawler
     * Time complexity is O(1)
     * @param newAge
     */
    void setAge (int newAge) {
        age = newAge;
    }

    /**
     * Attribute of web crawler
     * Time complexity is O(1)
     * @param newRelevance
     */
    void setRelevance (int newRelevance) {
        relevance = newRelevance;
    }

    /**
     * Attribute of web crawler
     * Time complexity is O(1)
     * @param newAds
     */
    void setAds (int newAds) {
        ads = newAds;
    }

    void setIndex(int newIndex) {
        index = newIndex;
    }

    /**
     * This function count occurrences of a keyword in the text of a web page
     * Time complexity is O(n), where n is the number of words in the text
     * @param keyword
     * @param a1
     * @param a2
     * @param a3
     * @param a4
     * @return score
     */
    public void CalculateScore(String keyword, double a1, double a2, double a3, double a4) {
        String a[] = text.split(" ");
        int count = 0;
        for (int i = 0; i < a.length; i++) {
            if (keyword.equals(a[i])) {
                count++;
            }
        }
        frequency = count;

        // The formula of calculation of the total score of each web page information
        score = a1 * frequency + a2 * age + a3 * relevance + a4 * ads;

    }

    /**
     * Get the calculated score
     * Time complexity is O(1)
     * @return
     */
    public double get_CalculateScore() {
        return score;
    }

    /**
     * Compare two web pages' scores
     * Time complexity is O(1)
     * @param i
     * @return
     */
    public int compareTo(WebPageInformation i) {
        // If nothing to be comparable or the value is super small, then this.score is larger
        if (i == null) {
            return 1;
        }
        if (this.score - i.score > 0) {
            return 1;
        } else if (this.score - i.score < 0){
            return -1;
        } else {
            return 0;
        }
    }

    /**
     * Store the results after comparing
     * Return a string representation of the results
     * Time complexity is O(1)
     * @return
     */
    public String toString () {

        String s = "Idx " + this.index + " Total Score = " + this.score + " Four Factors = { " + frequency + ", " +
                age + ", " + relevance + ", " + ads + " } Company name = " + getCompanyName() + " URL " + this.url;
        return s;

    }

    /**
     * Get company's name after splitting url
     * @return
     */
    public String getCompanyName() {
        String string = url;
        String[] words = string.split("\\.", 5);
        return words[1];
    }
}

