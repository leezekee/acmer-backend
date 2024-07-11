package top.zekee.acmerbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.zekee.acmerbackend.pojo.Contest;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CFContestDto {
    String status;
    ArrayList<Contest> result;
}
