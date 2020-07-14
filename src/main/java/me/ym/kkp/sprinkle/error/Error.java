package me.ym.kkp.sprinkle.error;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Error{

    private String url;
    private String message;
    private Integer code;

   /* public Error(String url, String message, Integer code) {
        this.url = url;
        this.message = message;
        this.code = code;
    }*/
}
