# Java Access Control, Stop the Insanity!

![crazy pills](http://manifold.systems/images/crazypills.png)

Java access modifiers `public`, `protected`, `package-private`, `private` are enforced *both* at compile-time and at
runtime. Here Scott McKinney explains why this is insane and how access to internals could be made simpler and
type-safe.

## Crazy Talk

Developers use access modifiers as a means to separate API from implementation (aka encapsulation). The modifiers
provide a clean way to tell other developers, "Here's the stuff I intend for you to use, all the rest is internal to my
implementation."  In essence access modifiers help developers define, identify, and safely use an API.

Fortunately the Java compiler makes sure developers don't *unintentionally* cross the boundaries established by the
modifiers. There's no way we can call a private method illegally without the compiler complaining about it. So why does
the Java runtime also enforce access control when the compiler has already done the work? Is it a form of *security*?
Sadly this is a widely held misconception. Allow me to explain. 

The lock on your front door is a form of security, right?  The lock's purpose is to prevent an intruder from entering
your home.  One thing you wouldn't do is devise a way for intruders to evade your security. And you certainly would not
advertise this information for all to see, right?  For example, you wouldn't post a sign on your front lawn revealing
the spare bedroom window you leave wide open specifically for intruders, right?  Otherwise by definition the lock on
your door is not a form of security, but is instead an indication of insanity.  

The Java Reflection API is the sign posted on the JVM's front lawn. It explains precisely how to bypass Java's runtime
access control; you can call any method you like using reflection. Therefore under normal circumstances Java runtime
access control is not in any logical way a form of security.

## Wishful Thinking

As previously stated under normal circumstances there is no reason the JVM should prevent bytecode from accessing a
type's internals since the compiler already does this for us. Furthermore there should be a way to use normal, type-safe
syntax to access internals if that is the developer's intention. Nothing is accomplished by making it hard and slow and
dangerous with reflection.

As a productive alternative the Java language could provide a simpler, type-safe syntax to access otherwise hidden
symbols:
```java
unsafe Foo foo = new Foo();
foo.privateMethod();
```
The `unsafe` modifier informs the compiler of your *intention* to type-safely use internal symbols of `Foo`. As such
the compiler grants `foo` with open access to `Foo`.  The advantages of this approach are significant:
* Your code is **type-safe**, the compiler verifies access to internal symbols
* If the internals change, your code breaks at **compile-time**, not runtime
* Your code is much **easier to read** and maintain
* Your code is visible to **static analysis tooling** 
* **Eliminates caching** and other complications associated with reflection

All of this could be achieved with or without the cooperation of the JVM.  If access control were removed from runtime,
great! Aside from the new `unsafe` modifier, nothing else is needed and performance is optimal.  Otherwise the compiler
could generate reflection code for usages of `unsafe` variables, still a big win.

## Back to Reality

Of course all of this is make believe, `unsafe` will never see the light of day in Redwood City. But should we give up?
[Was it over when the Germans bombed Pearl Harbor?](https://www.youtube.com/watch?v=Wv5c2YR1lVE) Heck no! Where there's
a will there's a way... 

Increasingly the [Manifold framework](http://manifold.systems) picks up where Java leaves off in terms of type system
shortcomings. In the case of access control Manifold provides a feature aptly named [Type-safe Reflection](https://github.com/manifold-systems/manifold/tree/master/manifold-deps-parent/manifold-ext#type-safe-reflection-via-jailbreak).
It is nearly identical to the `unsafe` proposal:
```java
@Jailbreak Foo foo = new Foo();
foo.privateMethod();
```       
As with `unsafe` the `@Jailbreak` annotation grants `foo` full access to `Foo`, all type-safe and compiler friendly.
Additionally IntelliJ IDEA provides comprehensive support for Manifold via plugin. Read more about Type-safe Reflection
and other features at [Manifold](http://manifold.systems).  
 

 