# Deadline

Modify this file to satisfy a submission requirement related to the project
deadline. Please keep this file organized using Markdown. If you click on
this file in your GitHub repository website, then you will see that the
Markdown is transformed into nice looking HTML.

## Part 1: App Description

> Please provide a firendly description of your app, including the
> the primary functions available to users of the app. Be sure to
> describe exactly what APIs you are using and how they are connected
> in a meaningful way.

> **Also, include the GitHub `https` URL to your repository.**

My application allows the user to select a region from a dropdown, and will show a Wikipedia page related to
a country in that region. The user can generate different pages based on different locations in different regions.
I use the First Org API and the Wikipedia API. The dropdown has the fields required for the region parameter of the
First Org API, so once the user selects a region, the app retrieves countries in that region. Then, I parse through the
JSON for the First Org API, obtain the countries, and randomly retrieve countries that are my input for the Wikipedia API.
The app takes in a region, and displays information about a country in that region.

Github url: https://github.com/rmathew17/cs1302-api.git
## Part 2: New

> What is something new and/or exciting that you learned from working
> on this project?

I liked being able to see the possible JSON fields and working to see what I parsed and how it would affect the results of the
second API. I liked the process of implementing and retrieving the responses, and being able to map these responses
onto my App.

## Part 3: Retrospect

> If you could start the project over from scratch, what do
> you think might do differently and why?

I would have come up with my idea and confirmed it sooner. Even though I read the project instructions,
I never truly considered about what it means for input to be used in a meaningful way.
My original API pairs could have been used alone, so I would have started brainstorming ideas earlier,
and should have considered my ideas in a more specific context, so I could truly understand whether they were used
properly or not.
