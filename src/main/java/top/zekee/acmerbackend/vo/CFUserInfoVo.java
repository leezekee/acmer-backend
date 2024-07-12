package top.zekee.acmerbackend.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.zekee.acmerbackend.dto.CFUserInfoDto;
import top.zekee.acmerbackend.pojo.CFUser;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CFUserInfoVo {
    List<CFUser> cfUsers = new ArrayList<>();
}
