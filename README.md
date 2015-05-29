# 109
Project

## Setup
In your terminal, go to a folder you want the final project in.  Clone the repository with the command:
`git clone https://github.com/brandonbeckhardt/109`

Once cloned, go into the repository:
`cd 109`

## Notes
Any changes you make on your local repository will not impact the master branch on git.  To update the information you have on your computer and to update the information on the git repository, read below.

## Pulling from github

To get the most recent version of the code run:
`git pull`

Note, this will override your local changes if you have no committed them.  Try to avoid pulling with uncommitted changes.  If you must do so, read about [Stashing](https://git-scm.com/book/no-nb/v1/Git-Tools-Stashing).


## Pushing to github
When you make changes locally you want to add to github run.
'git add .'
This will tell git what changes you want to add to the repository.  There are other commands you can use if you only want to add specific things.  This is usually the safest if you just want to add all of the changes you've made.


To give a name and description to all of the changes you've made, run:
`git commit`
 

Then, to get the code to the master branch on git, run:
`git push origin master`




