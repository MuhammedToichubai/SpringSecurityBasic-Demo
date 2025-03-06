package peaksoft.springsecuritybasicdemo.dto;

import lombok.Data;

import java.util.List;

@Data
public class PaginationResponse<T> {
    private int pageNumber;
    private int pageSize;
    private long totalPages;
    private long totalElements;
    private List<T> objects;
}
