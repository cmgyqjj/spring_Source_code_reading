/*
 * Copyright 2002-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.context.support;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.lang.Nullable;

/**
 * Standalone XML application context, taking the context definition files
 * from the file system or from URLs, interpreting plain paths as relative
 * file system locations (e.g. "mydir/myfile.txt"). Useful for test harnesses
 * as well as for standalone environments.
 *
 * <p><b>NOTE:</b> Plain paths will always be interpreted as relative
 * to the current VM working directory, even if they start with a slash.
 * (This is consistent with the semantics in a Servlet container.)
 * <b>Use an explicit "file:" prefix to enforce an absolute file path.</b>
 *
 * <p>The config location defaults can be overridden via {@link #getConfigLocations},
 * Config locations can either denote concrete files like "/myfiles/context.xml"
 * or Ant-style patterns like "/myfiles/*-context.xml" (see the
 * {@link org.springframework.util.AntPathMatcher} javadoc for pattern details).
 *
 * <p>Note: In case of multiple config locations, later bean definitions will
 * override ones defined in earlier loaded files. This can be leveraged to
 * deliberately override certain bean definitions via an extra XML file.
 *
 * <p><b>This is a simple, one-stop shop convenience ApplicationContext.
 * Consider using the {@link GenericApplicationContext} class in combination
 * with an {@link org.springframework.beans.factory.xml.XmlBeanDefinitionReader}
 * for more flexible context setup.</b>
 *
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @see #getResource
 * @see #getResourceByPath
 * @see GenericApplicationContext
 */
public class FileSystemXmlApplicationContext extends AbstractXmlApplicationContext {

	public FileSystemXmlApplicationContext() {
	}
	public FileSystemXmlApplicationContext(ApplicationContext parent) {
		super(parent);
	}
	public FileSystemXmlApplicationContext(String configLocation) throws BeansException {
		this(new String[] {configLocation}, true, null);
	}
	public FileSystemXmlApplicationContext(String... configLocations) throws BeansException {
		this(configLocations, true, null);
	}
	public FileSystemXmlApplicationContext(String[] configLocations, ApplicationContext parent) throws BeansException {
		this(configLocations, true, parent);
	}
	public FileSystemXmlApplicationContext(String[] configLocations, boolean refresh) throws BeansException {
		this(configLocations, refresh, null);
	}

	/**
	 *使用给定的父级创建一个新的 FileSystemXmlApplicationContext，
	 *从给定的XML文件加载定义。
	 *@param configLocations 文件路径数组
	 *@param refresh 是否自动刷新上下文，
	 *加载所有bean定义并创建所有singleton。
	 *或者，在进一步配置上下文后手动调用刷新。
	 *@param parent 父上下文
	 *如果上下文创建失败，@throws BeansException
	 *@参见#refresh（）
	 */
	public FileSystemXmlApplicationContext(
			String[] configLocations, boolean refresh, @Nullable ApplicationContext parent)
			throws BeansException {

		super(parent);
		setConfigLocations(configLocations);
		if (refresh) {
			refresh();
		}
	}

	/**
	 *将资源路径解析为文件系统路径。
	 *＜p＞注意：即使给定的路径以斜杠开头，它也会得到
	 *被解释为相对于当前VM工作目录。
	 *这与Servlet容器中的语义一致。
	 *@param资源的路径
	 *@return资源句柄
	 *@参见org.springframework.web.context.support.XmlWebApplicationContext#getResourceByPath
	 */
	@Override
	protected Resource getResourceByPath(String path) {
		if (path.startsWith("/")) {
			path = path.substring(1);
		}
		return new FileSystemResource(path);
	}
}
