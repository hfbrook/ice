// **********************************************************************
//
// Copyright (c) 2001
// Mutable Realms, Inc.
// Huntsville, AL, USA
//
// All Rights Reserved
//
// **********************************************************************

#ifndef TEST_ICE
#define TEST_ICE

class B;
class C;

class A
{
    B b;
    C c;
};

class B extends A
{
    A a;
};

class C
{
    B b;
};

class D
{
    A a;
    B b;
    C c;    
};

class Initial
{
    void shutdown();
    B getB1();
    B getB2();
    C getC();
    D getD();
    void getAll(out B b1, out B b2, out C c, out D d);
    void addFacetsToB1();
};

#endif
