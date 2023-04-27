public class Frame {
    private Page page;
    private String name;

    public Frame(String name) {
        this.name = name;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public Page getPage() {
        return page;
    }

    public String getName() {
        return name;
    }

    public void incrementAge() {
        if (page != null)
            page.incrementAge();
    }
}
