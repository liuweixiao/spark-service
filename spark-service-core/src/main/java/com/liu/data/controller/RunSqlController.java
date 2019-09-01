package com.liu.data.controller;

import com.liu.data.PlatformManger;
import com.liu.data.model.ResponseResults;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(description = "sql 服务查询 api")
@RestController
public class RunSqlController {


  @ApiOperation(value = "查询sql", notes = "")
  @ApiImplicitParams({
          @ApiImplicitParam(name = "sql", value = "查询 sql", required = true, dataType = "String")
  })
  @RequestMapping(value = "/runSql", method = {RequestMethod.POST, RequestMethod.GET})
  public ResponseResults runSql(@RequestParam String sql) {

    try {
      return ResponseResults.ok(PlatformManger.runSql(sql));
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException(e.getMessage());
    }
  }


  @ApiOperation(value = "执行脚本", notes = "")
  @ApiImplicitParams({
          @ApiImplicitParam(name = "sql", value = "查询脚本语句", required = true, dataType = "String")
  })
  @RequestMapping(value = "/runScript", method = {RequestMethod.POST, RequestMethod.GET})
  public ResponseResults runScript(@RequestParam String sql) {

    try {

      return ResponseResults.ok(PlatformManger.runScript(sql));
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException(e.getMessage());
    }
  }



}
