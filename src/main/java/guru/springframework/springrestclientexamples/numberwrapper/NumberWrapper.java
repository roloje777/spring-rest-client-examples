package guru.springframework.springrestclientexamples.numberwrapper;

public class NumberWrapper {
    private Integer limit;

    public NumberWrapper(Integer limit) {
        this.limit = limit;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    @Override
    public String toString() {
        return "NumberWrapper{" +
                "limit=" + limit +
                '}';
    }
}
