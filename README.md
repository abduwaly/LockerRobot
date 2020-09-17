## Locker Robot

### 目标：
我们要开发一个新的Locker Robot存取包系统，其中Locker/Robot/Manager可以帮助顾客存取包。
###背景：
随着互联网智能时代的快速发展，华顺超市也准备将之前的人工存取包变得更加智能化，可以让小樱(前台服务员) 一个人就可以搞定大量的存取包服务。所以特聘请你来为他们开发这个LockerRobot存取包系统。

### 业务需求如下：
华顺超市准备购买三种型号的储物柜，分别为S，M，L（S < M < L）。当顾客来存包的时候只需要将包交给小樱，之后的一系列存包会由小樱来完成。
小樱在存包之前先会拿到包裹的尺寸标签，根据不同的尺寸标签决定是直接存入Locker还是找对应Robot存包。当包裹尺寸为S时，小樱会直接存入S号的Locker中；当包裹尺寸为M时，找PrimaryLockerRobot存包；当包裹尺寸为L时，找SuperLockerRobot存包。存包成功后小樱会将票据交给顾客。存包的时候，小樱从不犯糊涂，她一定能找对目标。

当普通顾客拿着票据来取包的时候，只要把票据交给小樱，小樱会找对应的Robot或者Locker取包。
当VIP顾客来存取包时，可以直接通过VIP通道找LockerRobotManager提供专门的存取包服务。

### 业务规则
1. Locker可以存包取包
2. PrimaryLockerRobot 按照Locker顺序存，它只管理M号Locker，暂且不用考虑管理其它型号的Locker
3. SuperLockerRobot 将包存入空置率(可用容量/容量)最大的Locker，它只管理L号Locker，暂且不用考虑管理其它型号的Locker
4. 目前由于业务量比较小，LockerRobotManager只管理一个Locker（S号）、一个PrimaryLockerRobot（管理一个Locker）和SuperLockerRobot（管理一个Locker），但也不排除后期随着业务增长，LockerRobotManager会管理更多的Locker或者Robot
5. LockerRobotManager可以委派Robot存包取包，也可以自己存包取包，委派顺序没有要求
6. LockerRobotManager管理的Locker和Robot不会直接对外提供服务
7. 不同型号Locker产生的票据不通用，当用不同的型号票取包时，系统要提示票的型号不对
8. 超市管理员在配置Robot和Manager的时候，只要Locker的型号选择不对，Robot和Manager将无法正常使用


### 常见问题
1. 不存在容量为0的Locker，Robot至少要管理一个Locker
2. M，L号的Locker不对外提供服务，只能通过PrimaryLockerRobot或者SuperLockerRobot进行使用
3. 小樱会在线下对票据进行区分找不同的robot或者Locker进行取包，但她难免也有犯糊涂的时候。
4. 对于非VIP顾客找LockerRobotManager进行存取包，是线下验证还是系统验证？
5. VIP通道非VIP顾客是没法进入的。
6. LockerRobotManager管理的robot的locker可以和其他robot的locker是相同的吗？
    >不能相同，如果相同，则配置无效，将无法正常使用。
7. 小樱能区分不同类型的票据，那能够区分伪造的票据吗？
    >从实际场景出发，小樱不能够区分伪造票
8. 小樱代理用户取完包后，会回收票据吗？
    >小樱会回收，但她自己取包的时候难免也有犯糊涂的时候。

---

### Tasking

```
#### 普通用户（通过小樱）存包相关的场景

1. 
Given 一个小包和一个S号Locker有容量 
When 存包 
Then 存包成功，并返回票据

2. 
Given 一个小包和一个S号Locker没有容量 
When 存包 
Then 存包失败，并提示无可用容量

3. 
Given 一个中包，一个PrimaryLockerRobot管理两个M号Locker，并且两个Locker都有容量 
When 存包 
Then 存到第一个Locker，并返回票据

4. 
Given 一个中包，一个PrimaryLockerRobot管理两个M号Locker，并且第一个Locker无容量而第二个有容量
When 存包
Then 存到第二个Locker，并返回票据

5. 
Given 一个中包，一个PrimaryLockerRobot管理两个M号Locker，并且两个Locker都没有容量
When 存包
Then 存包失败，并提示无可用容量

6. 
Given 一个大包，一个SuperLockerRobot管理两个L号Locker，并且两个Locker都有容量，且第二个Locker空置率更高
When 存包 
Then 存到第二个Locker，并返回票据

7. 
Given 一个大包，一个SuperLockerRobot管理两个L号Locker，并且第一个Locker无容量而第二个有容量
When 存包
Then 存到第二个Locker，并返回票据

8. 
Given 一个大包，一个SuperLockerRobot管理两个L号Locker，并且两个Locker都没有容量
When 存包
Then 存包失败，并提示无可用容量

#### 普通用户（通过小樱）取包相关的场景

9. Given 一个存储小包的票据 When 取包 Then 成功从Locker取包

10. Given 一个存储中包的票据 When 取包 Then 成功由PrimaryLockerRobot取包

11. Given 一个存储大包的票据 When 取包 Then 成功由SuperLockerRobot取包

12. Given 一个存储大包的票据 When 尝试从Locker取包 Then 取包失败，并提示未找到包的错误

13. Given 一个存储中包的票据 When 尝试由SuperLockerRobot取包 Then 取包失败，并提示未找到包的错误

14. Given 一个存储小包的票据 When 尝试由PrimaryLockerRobot取包 Then 取包失败，并提示未找到包的错误

15. Given 一个存储小包的造假票据 When 尝试从Locker取包 Then 取包失败，并提示无效票据

16. Given 一个存储中包的造假票据 When 尝试由PrimaryLockerRobot取包 Then 取包失败，并提示无效票据

17. Given 一个存储大包的造假票据 When 尝试由SuperLockerRobot取包 Then 取包失败，并提示无效票据

#### VIP客户存包场景 （VIP身份线下判断）

18. Given 一个小包，一个LockerRobotManager管理一个Locker并有容量 When 存包 Then 成功存包到Locker，并返回票据

19. Given 一个小包，一个LockerRobotManager管理一个Locker而无容量 When 存包 Then 存包失败，并提示无可用容量

20. 
Given 一个中包，一个LockerRobotManager管理一个的PrimaryLockerRobot管理下的Locker有容量 
When 存包
Then 成功由由存PrimaryLockerRobot存包到其管理的Locker，并返回票据

21.
Given 一个中包，一个LockerRobotManager管理一个的PrimaryLockerRobot管理下的Locker无容量 
When 存包 
Then 存包失败，并提示无可用容量

22. 
Given 一个大包，一个LockerRobotManager管理一个的SuperLockerRobot管理下的Locker有容量 
When 存包
Then 成功由存SuperLockerRobot存包到其管理的Locker，并返回票据

23. 
Given 一个大包，一个LockerRobotManager管理一个的SuperLockerRobot管理下的Locker无容量 
When 存包 
Then 存包失败，并提示无可用容量

#### VIP用户取包场景

24. Given 一个存储小包的票据，一个LockerRobotManager When 取包 Then 成功从Locker取包

25. Given 一个存储中包的票据，一个LockerRobotManager When 取包 Then 成功由其管理的PrimaryLockerRobot取包

26. Given 一个存储大包的票据，一个LockerRobotManager When 取包 Then 成功由其管理的SuperLockerRobot取包

27. Given 一个无效的票据，一个LockerRobotManager When 尝试从Locker取包 Then 取包失败，并提示无效票据

#### 小樱存包时犯糊涂搞错的场景

28. Given 一个小包 When 委托PrimaryLockerRobot存包 Then 存包失败，并提示包不匹配错误

29. Given 一个中包 When 委托SuperLockerRobot存包 Then 存包失败，并提示包不匹配错误

30. Given 一个大包 When 尝试存包到Locker Then 存包失败，并提示包不匹配错误

#### 配置不匹配的Locker给Robots 或者 Manager 的场景

31. Given 一个M号Locker When 超市管理员配置给LockerRobotManager Then 配置失败，提示Locker类型不匹配

32. Given 一个S号Locker When 超市管理员配置给SuperLockerRobot Then 配置失败，提示Locker类型不匹配

33. Given 一个L号Locker When 超市管理员配置给PrimaryLockerRobot Then 配置失败，提示Locker类型不匹配

```
