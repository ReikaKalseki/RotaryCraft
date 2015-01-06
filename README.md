RotaryCraft
===========

The source code to my tech mod RotaryCraft.

@author Reika

Copyright 2013

All rights reserved.
Distribution of the software in any form is only allowed with
explicit, prior permission from the owner.
See [License.txt](License.txt) and the [official licensing page](https://sites.google.com/site/reikasminecraft/licensing) for more details.


Pull Requests
=============
If you wish to suggest code, I actually do not merge pull requests, for two reasons:

One, I have had very bad experiences with errors on GitHub's part when accepting pull requests. The first and last time I did so, because of the timing of the PR relative to other commits (I noticed the PR about two weeks after it was submitted, and made commits, including to the changed files, in the interim), it corrupted the entire repository and the code on my machine, forcing me to roll back 3-6 months worth of commits. The only reason I did not lose that much code is because I had another copy on another computer, so I was able to roll it back (with the code safely backed up), then re-load the backup and make one commit with 3-6 months of changes. Because I do not regularly check GitHub, this situation would be very likely to repeat itself.

Two, the code is not written by me and thus I do not have a full understanding of how it works; even if I can trace the actual program logic, I know little of why it is designed the way it is - any programmer will agree that for any non-trivial algorithm, they understand their code better than anyone else - and much more importantly, nothing about how to change it if I need to fix or change it later, especially in the cases of Minecraft version updates. Use of supplied code has already introduced bugs and crashes for this exact reason.
