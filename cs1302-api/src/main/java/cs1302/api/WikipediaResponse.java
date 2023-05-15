package cs1302.api;

import java.util.Map;

/**
 * Represents a response from the Wikipedia API.
 */

public class WikipediaResponse {

    private Query query;

    /**
     * Returns the query object in the response.
     * @return the query object
     */

    public Query getQuery() {
        return query;
    } //getQuery

    /**
     * Sets the query object in the response.
     *@param query the query object to set
     */
    public void setQuery(Query query) {
        this.query = query;
    } //setQuery

    /**
     * Returns the extract of the first page in the query.
     * If the query is null, or it contains no pages, returns an empty string.
     * @return the extract of the first page in the query, or an empty string
     */

    public String getExtract() {
        if (query == null || query.getPages() == null || query.getPages().isEmpty()) {
            return "";
        } //if
        return query.getPages().values().iterator().next().getExtract();
    } //getExtract

    /**
     * Represents a query object in a Wikipedia API response.
     */
    public static class Query {

        private Map<String, Page> pages;

        /**
         * Returns the map of pages in the query.
         *@return the map of pages
         */
        public Map<String, Page> getPages() {
            return pages;
        } //getPages

        /**
         * Sets the map of pages in the query.
         * @param pages the map of pages to set
         */
        public void setPages(Map<String, Page> pages) {
            this.pages = pages;
        } //setPages

    } //Query

    /**
     * Represents a page object in a Wikipedia API response.
     */
    public static class Page {
        private String extract;

        /**
         * Returns the extract of the page.
         * @return the extract
         */
        public String getExtract() {
            return extract;
        } //getExtract
        /**

         * Sets the extract of the page.
         * @param extract the extract to set
         */

        public void setExtract(String extract) {
            this.extract = extract;
        } //setExtract

    } //Page

} //WikipediaResponse
