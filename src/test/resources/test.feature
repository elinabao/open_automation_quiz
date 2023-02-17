Feature: 填写表单并提交
  Scenario: 填写表单并提交成功
    Given 进入页面
    When 选中 连续生产/开工类企事业单 代码（"ffwi"）截图并提交
    And 填写第二页表单截图并提交
      | date      | 2023-01-01  |
      | user      | 自动化         |
      | telephone | 13888888888 |
    And 填写第三页表单截图并提交
      | company   | 测试公司        |
      | number    | 99          |
      | charge    | 包娜          |
      | date      | 2023-02-01  |
      | hubie     | 0           |
      | telephone | 13888888888 |
      | caseName  | 测试内容        |

    Then 验证提交成功