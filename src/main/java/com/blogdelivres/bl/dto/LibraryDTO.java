package com.blogdelivres.bl.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LibraryDTO {
    private Long libraryId;
    private String libraryName;
}
