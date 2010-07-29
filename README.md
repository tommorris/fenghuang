**Fenghuang** is a Scala [FireEagle][] library I'm working on. It isn't ready
to be used, but it will depend on: [JodaTime][] and [Databinder
Dispatch][dispatch].  It is [BDD][]-style unit tested using [Specs][], and the
['mock traits' pattern I came up with][mocktraits].

FireEagle is basically location-based services done right.

I may add support for [GeoTools][], but it is *bloody complicated*. I may just
reuse the [jFireEagle][] library classes.

Patches welcome.

[FireEagle]: http://fireeagle.yahoo.com/
[JodaTime]: http://joda-time.sourceforge.net/
[Dispatch]: http://dispatch.databinder.net/
[BDD]: http://behaviour-driven.org/
[Specs]: http://code.google.com/p/specs/
[mocktraits]: http://tommorris.org/blog/2010/06/26#When:08:29:58
[GeoTools]: http://www.geotools.org/
[jFireEagle]: http://code.google.com/p/jfireeagle/
