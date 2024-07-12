package top.zekee.acmerbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.zekee.acmerbackend.pojo.CFUser;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CFUserInfoDto {
    String status;
    List<CFUser> result;
}
