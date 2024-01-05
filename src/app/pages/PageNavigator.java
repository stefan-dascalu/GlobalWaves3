package app.pages;

import lombok.Getter;

import java.util.Stack;

public final class PageNavigator {
    private final Stack<Page> nextPages;
    private final Stack<Page> previousPages;

    @Getter
    private Page currentPage;
    private final String username;

    public PageNavigator(final Page page, final String username) {
        currentPage = page;
        this.username = username;

        nextPages = new Stack<>();
        previousPages = new Stack<>();
    }

    /**
     * Previous page.
     *
     * @return the string
     */
    public String previousPage() {
        if (previousPages.isEmpty()) {
            return "There are no pages left to go back.";
        }

        nextPages.push(currentPage);
        currentPage = previousPages.pop();
        return "The user %s has navigated successfully to the previous page.".formatted(username);
    }

    /**
     * Next page.
     *
     * @return the strin
     */
    public String nextPage() {
        if (nextPages.isEmpty()) {
            return "There are no pages left to go forward.";
        }

        previousPages.push(currentPage);
        currentPage = nextPages.pop();
        return "The user %s has navigated successfully to the next page.".formatted(username);
    }

    /**
     * Changes current page.
     *
     * @param page the page
     */
    public void changePage(final Page page) {
        if (currentPage != null) {
            previousPages.push(currentPage);
        }
        currentPage = page;
        nextPages.clear();
    }
}
