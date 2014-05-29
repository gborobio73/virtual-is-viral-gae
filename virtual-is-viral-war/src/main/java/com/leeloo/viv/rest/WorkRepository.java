package com.leeloo.viv.rest;

import java.util.*;

public class WorkRepository{

	private List<Work> works = Arrays.asList(
    		new Work("1", "erika.perttuli@gmail.com","Building facade", "A picture of a building facade", "1"),
            new Work("2", "erika.perttuli@gmail.com","Anoter work", "Another work", "2"),
            new Work("3", "leeloo.turku@gmail.com","Painting", "A painting work", "3"),
            new Work("4", "leeloo.turku@gmail.com","Writting", "A postal card work", "4"),
            new Work("5", "gborobio@gmail.com", "A picture work", "A picture of Leeloo", "5"),
            new Work("6", "erika.perttuli@gmail.com","Building facade", "A picture of a building facade", "6"),
            new Work("7", "erika.perttuli@gmail.com","Anoter work", "Another work", "7"),
            new Work("8", "leeloo.turku@gmail.com","Painting", "A painting work", "8"),
            new Work("9", "leeloo.turku@gmail.com","Writting", "A postal card work", "9"),
            new Work("10", "gborobio@gmail.com", "A picture work", "A picture of Borobio", "10"),
            new Work("11", "erika.perttuli@gmail.com", "A picture work", "A picture of Erika", "11"),
            new Work("12", "gborobio@gmail.com", "A picture work", "A picture of Borobio", "12"),
            new Work("13", "leeloo.turku@gmail.com", "A picture work", "A picture of Leeloo", "13"),
            new Work("14", "gborobio@gmail.com", "A picture work", "A picture of Borobio", "14")
    	);


    public List<Work>  getAllWorks()
    {
        return works;
    }

    public List<Work> getUserWorks(String user)
    {
    	List<Work> userWorks = new ArrayList<Work>();
    	for(Work work : works)
		{
		    if (work.User.toLowerCase().equals(user.toLowerCase())) {
		    	userWorks.add(work);
		    }
		}
        return userWorks;
    }

    public Work getWork(String id)
    {
        for(Work work : works)
        {
            if (work.Id.toLowerCase().equals(id.toLowerCase())) {
                return work;
            }
        }
        return new NullWork();
    }
}