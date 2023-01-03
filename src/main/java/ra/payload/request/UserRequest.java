package ra.payload.request;

import lombok.Data;

@Data
public class UserRequest {
    private String email;
    private String phone;
}
