// **********************************************************************
//
// Copyright (c) 2001
// Mutable Realms, Inc.
// Huntsville, AL, USA
//
// All Rights Reserved
//
// **********************************************************************

class RunParser
{
    static void
    usage(String appName)
    {
	System.err.println("Usage: " + appName + " [options] [file...]\n");
	System.err.print(
	    "Options:\n" +
	    "-h, --help           Show this message.\n");
	//"-v, --version        Display the Ice version.\n"
    }

    static int
    runParser(String appName, String[] args, Ice.Communicator communicator)
    {
	String file = null;
	int idx = 0;

	while(idx < args.length)
	{
	    if(args[idx].equals("-h") | args[idx].equals("--help"))
	    {
		usage(appName);
		return 0;
	    }
/*
  else if(args[idx].equals("-v") || args[idx].equals("--version"))
  {
  cout + ICE_STRING_VERSION + endl;
  return 0;
  }
*/
	    else if(args[idx].charAt(0) == '-')
	    {
		System.err.println(appName + ": unknown option `" + args[idx] + "'");
		usage(appName);
		return 1;
	    }
	    else
	    {
		if(file == null)
		{
		    file = args[idx];
		}
		else
		{
		    System.err.println(appName + ": only one file is supported.");
		    usage(appName);
		    return 1;
		}
		++idx;
	    }
	}

	Ice.Properties properties = communicator.getProperties();
	String refProperty = "Library.Library";
	String ref = properties.getProperty(refProperty);
	if(ref.length() == 0)
	{
	    System.err.println(appName +  ": property `" + refProperty + "' not set");
	    return 1;
	}

	Ice.ObjectPrx base = communicator.stringToProxy(ref);
	LibraryPrx library = LibraryPrxHelper.checkedCast(base);
	if(library == null)
	{
	    System.err.println(appName + ": invalid object reference");
	    return 1;
	}

	Parser parser = new Parser(communicator, library);
	int status;

	if(file == null)
	{
	    status = parser.parse();
	}
	else
	{
	    try
	    {
		status = parser.parse(new java.io.BufferedReader(new java.io.FileReader(file)));
	    }
	    catch(java.io.IOException ex)
	    {
		status = 1;
		ex.printStackTrace();
	    }
	}

	return status;
    }
}
