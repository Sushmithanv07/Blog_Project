package com.myblog8.payloadDTO;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data  //(if this doesn't work use both @Setter and @Getter as its alternative)
@Setter
@Getter
@AllArgsConstructor // we are achieving encapsulation
@NoArgsConstructor  // we are achieving encapsulation

public class PostDto {

    private Long id;

    @NotEmpty
    @Size(min = 2, message = "Post title should have at least 2 characters")
    private String title;

    @NotEmpty
    @Size(min = 10, message = "Post title should have at least 10 characters")
    private String description;

    @NotEmpty
    private String content;

}
