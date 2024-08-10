console.log("script loaded");


let currentTheme=getTheme();
//console.log(currentTheme);

changeTheme();
// todo
function changeTheme(){


    // set to web page
    document.querySelector("html").classList.add(currentTheme);


    // set the listener to change theme button
    const changeThemeButton = document.querySelector("#theme_change_button");
    changeThemeButton.addEventListener("click",(event) => {console.log("change theme button clicked");
         
        const oldTheme = currentTheme;

        if(currentTheme == "dark" ){
            currentTheme = "light";
        }else{
            currentTheme = "dark";
        }

        // local storage me update karenge
        setTheme(currentTheme);
        //remove the current theme
        document.querySelector("html").classList.remove(oldTheme);

        // set the current theme
        document.querySelector("html").classList.add(currentTheme);
    })
}

// set theme to local storage

function setTheme(theme){
    localStorage.setItem("theme",theme);
}

// get theme to local storage

function getTheme(){
    let theme=localStorage.getItem("theme");
    if(theme) 
        return theme;
    else 
        return "light";
}