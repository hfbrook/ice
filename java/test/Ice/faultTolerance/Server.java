// **********************************************************************
//
// Copyright (c) 2001
// Mutable Realms, Inc.
// Huntsville, AL, USA
//
// All Rights Reserved
//
// **********************************************************************

public class Server
{
    private static void
    usage()
    {
        System.err.println("Usage: Server port");
    }

    private static int
    run(String[] args, Ice.Communicator communicator)
    {
        int port = 0;
        for(int i = 0; i < args.length; i++)
        {
            if(args[i].charAt(0) == '-')
            {
                //
                // TODO: Arguments recognized by the communicator are not
                // removed from the argument list.
                //
                //System.err.println("Server: unknown option `" + args[i] + "'");
                //usage();
                //return 1;
                continue;
            }

            if(port > 0)
            {
                System.err.println("Server: only one port can be specified");
                usage();
                return 1;
            }

            try
            {
                port = Integer.parseInt(args[i]);
            }
            catch(NumberFormatException ex)
            {
                System.err.println("Server: invalid port");
                usage();
                return 1;
            }
        }

        if(port <= 0)
        {
            System.err.println("Server: no port specified");
            usage();
            return 1;
        }

        String endpts = "default -p " + port;
        Ice.ObjectAdapter adapter =
            communicator.createObjectAdapterWithEndpoints("TestAdapter",
                                                          endpts);
        Ice.Object object = new TestI(adapter);
        adapter.add(object, Ice.Util.stringToIdentity("test"));
        adapter.activate();
        communicator.waitForShutdown();
        return 0;
    }

    public static void
    main(String[] args)
    {
        int status = 0;
        Ice.Communicator communicator = null;

        try
        {
            communicator = Ice.Util.initialize(args);
            status = run(args, communicator);
        }
        catch(Ice.LocalException ex)
        {
            ex.printStackTrace();
            status = 1;
        }

        if(communicator != null)
        {
            try
            {
                communicator.destroy();
            }
            catch(Ice.LocalException ex)
            {
                ex.printStackTrace();
                status = 1;
            }
        }

        System.exit(status);
    }
}
