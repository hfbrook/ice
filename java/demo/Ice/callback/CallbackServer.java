// **********************************************************************
//
// Copyright (c) 2001
// Mutable Realms, Inc.
// Huntsville, AL, USA
//
// All Rights Reserved
//
// **********************************************************************

class CallbackServer extends Ice.Application
{
    public int
    run(String[] args)
    {
        Ice.ObjectAdapter adapter =
            communicator().createObjectAdapter("CallbackAdapter");
        CallbackPrx self = CallbackPrxHelper.uncheckedCast(
            adapter.createProxy(Ice.Util.stringToIdentity("callback")));
        adapter.add(new CallbackI(communicator()),
                    Ice.Util.stringToIdentity("callback"));
        adapter.activate();
        communicator().waitForShutdown();
        return 0;
    }
}
