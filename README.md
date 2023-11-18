# Volume In Time Manager
Simple tool for scheduling phone voices turned on/off in particular time interval.
## Why does it even exist?
Although it might seem unnecessary, I find it irritating to have to remember to toggle the sound on in my phone after a day of work in the office.
## Technologies used
In the early version (year 2020) it was built on XML Views, Broadcast Receivers and files with JSON values providing toggle rules for sounds on/off. I was using Java as main language.

I decided to update technologies and use:
- Kotlin as main language
- Compose for views
- Room database
- Hilt(Dagger) for Depedency Injection (Room database injected to Compose Views)
- Toggling sounds on/off in provided time: no decision so far
