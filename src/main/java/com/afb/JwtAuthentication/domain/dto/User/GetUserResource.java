package com.afb.JwtAuthentication.domain.dto.User;

import com.afb.JwtAuthentication.domain.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetUserResource {
    private String username;
    private LocalDateTime dateCreated;
    private Boolean active;

    public GetUserResource(User user){
        this.username = user.getUsername();
        this.dateCreated = user.getDateCreated();
        this.active = user.getActive();
    }

    public static List<GetUserResource> convert(List<User> users){
        return users.stream().map(GetUserResource::new).collect(Collectors.toList());
    }
}



