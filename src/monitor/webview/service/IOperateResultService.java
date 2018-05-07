package monitor.webview.service;

import java.util.List;
import java.util.Map;

import monitor.webview.entity.Page;

public interface IOperateResultService {
	
	public List<Page> getPublishJob(Page job);
	public int getJobTotalNumber(Page job);
	public int delete(Page job);
	public Page getResultDetailPage(Page job);
	public int getJobById(Page job);
	
}
