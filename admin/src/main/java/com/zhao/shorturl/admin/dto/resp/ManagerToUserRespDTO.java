package com.zhao.shorturl.admin.dto.resp;

import com.zhao.shorturl.admin.dao.entity.ManagerToUserDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: 小赵
 * @DateTime: 2025/3/23 18:42
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ManagerToUserRespDTO {

    private List<ManagerToUserDO> assignedList;

    private List<ManagerToUserDO> unAssignedList;

    private Integer assignedTotal;

    private Integer unassignedTotal;

}
