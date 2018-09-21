package com.pascloud.core.script;

public interface IIdScript extends IScript {
	
	/**
	 * 
	 * @return 脚本ID，一般用于处理特殊的逻辑，策划配置的ID
	 */
    int getModelID();

}
