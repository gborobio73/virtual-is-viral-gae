package com.leeloo.viv.rest.jsonpojos;

import java.util.ArrayList;
import java.util.List;

import com.leeloo.viv.work.Work;

public class UiWorkMapper {

	public List<UiWork> map (List<Work> works)
	{
		List<UiWork> uiWorks = new ArrayList<UiWork>();
		for(Work work : works)
		{
			uiWorks.add(map(work));
		}
		return uiWorks;
	}
	
	public UiWork map (Work work)
	{
		return new UiWork(work);
	}
}
