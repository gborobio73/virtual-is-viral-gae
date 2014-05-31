package com.leeloo.viv.rest;

import java.util.*;

public class WorkRepository{

	private List<Work> works = Arrays.asList(
    		new Work("1", "erika.perttuli","Building facade", "A picture of a building facade", "1", 
    				Arrays.asList(
    						new Comment("1", "leeloo.turku", "How beautiful! Good job!"),
    						new Comment("2", "erika.perttuli", "Bah, you need to work harder."),
    						new Comment("1", "leeloo.turku", "Erika, that is a good job!")
    						)),
            new Work("2", "erika.perttuli","Anoter work", "Another work", "2", new ArrayList<Comment>()),
            new Work("3", "leeloo.turku","Painting", "A painting work", "3", new ArrayList<Comment>()),
            new Work("4", "leeloo.turku","Writting", "A postal card work", "4", new ArrayList<Comment>()),
            new Work("5", "gborobio", "A picture work", "A picture of Leeloo", "5", new ArrayList<Comment>()),
            new Work("6", "erika.perttuli","Building facade", "A picture of a building facade", "6", new ArrayList<Comment>()),
            new Work("7", "erika.perttuli","Anoter work", "Another work", "7", new ArrayList<Comment>()),
            new Work("8", "leeloo.turku","Painting", "A painting work", "8", new ArrayList<Comment>()),
            new Work("9", "leeloo.turku","Writting", "A postal card work", "9", new ArrayList<Comment>()),
            new Work("10", "gborobio", "A picture work", "A picture of Borobio", "10", new ArrayList<Comment>()),
            new Work("11", "erika.perttuli", "A picture work", "A picture of Erika", "11", new ArrayList<Comment>()),
            new Work("12", "gborobio", "A picture work", "A picture of Borobio", "12", new ArrayList<Comment>()),
            new Work("13", "leeloo.turku", "A picture work", "A picture of Leeloo", "13", new ArrayList<Comment>()),
            new Work("14", "gborobio", "A picture work", "A picture of Borobio", "14", new ArrayList<Comment>())
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