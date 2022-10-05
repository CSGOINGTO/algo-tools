package com.lx.processors.annotation;

/**
 * 处理@MainMethod注解
 * <p>
 * 功能
 * 1. 扫描当前项目中所有标记@MainMethod的类和方法
 * 2. 将标记@MainMethod方法所对应的类中的所有的AlgoMethod汇总
 * 3. 为每个AlgoMethod创建一个对应的boolean类型的全局参数param
 * 4. 为每个AlgoMethod单独创建一个方法mainMethodProcessAlgoMethod，在该方法中单独调用AlgoMethod，并在AlgoMethod执行结束后，修改全局参数param的值，用来标记AlgoMethod已经执行完毕
 * 5. 每个AlgoMethod对应一个DrawImageTask，用于绘制algoMethod所对应的GIF图片
 * 6. 每个AlgoMethod对应一个DrawImageListener，用于监听algoMethod是否执行完毕  TODO 还需要提供一个实时监听algoMethod方法内部数据结构实时变化的监听器
 */
public class MainMethodProcessor {

}
