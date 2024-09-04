package com.gongcha.berrymatch.group;


import com.gongcha.berrymatch.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class GroupController {


    @Autowired
    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }


    @PostMapping("/creategroup")
    public ApiResponse<GroupResponse> createGroup(@RequestBody GroupRequest groupRequest) {
        try {
            GroupResponse groupResponse = groupService.createGroup(groupRequest);
            return ApiResponse.ok(groupResponse);
        } catch (IllegalStateException e) {
            return ApiResponse.of(HttpStatus.BAD_REQUEST, e.getMessage(), null);
        }
    }


    @PostMapping("/joingroup")
    public ApiResponse<GroupResponse> joinGroup(@RequestBody GroupRequest groupRequest) {
        try {
            GroupResponse groupResponse = groupService.joinGroup(groupRequest);
            return ApiResponse.ok(groupResponse);
        } catch (IllegalStateException e) {
            return ApiResponse.of(HttpStatus.BAD_REQUEST, e.getMessage(), null);
        }
    }

    @PostMapping("/{groupCord}/leave")
    public ApiResponse<GroupResponse> leaveGroup(@PathVariable String groupCord,@RequestBody GroupRequest groupRequest){
        System.out.println(groupRequest);
        groupRequest.setGroupCord(groupCord);
        GroupResponse groupResponse = groupService.leaveGroup(groupRequest);
        return ApiResponse.ok(groupResponse);
    }




    @GetMapping("/{groupCord}/state")
    public ApiResponse<GroupResponse> getGroupState(@PathVariable String groupCord ,GroupRequest groupRequest  ) {
         GroupResponse groupResponse  = groupService.getGroupByCord(groupCord);
        return ApiResponse.ok(groupResponse);
    }
}
