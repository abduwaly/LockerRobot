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







 
