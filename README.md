<!-- Improved compatibility of back to top link: See: https://github.com/othneildrew/Best-README-Template/pull/73 -->
<a id="readme-top"></a>
<!--
*** Thanks for checking out the Best-README-Template. If you have a suggestion
*** that would make this better, please fork the repo and create a pull request
*** or simply open an issue with the tag "enhancement".
*** Don't forget to give the project a star!
*** Thanks again! Now go create something AMAZING! :D
-->
<!-- PROJECT LOGO -->
<br />
<div align="center">
<h3 align="center">LPrefixor</h3>

  <p align="center">
    This small Spigot Plugin was made by me a couple of years ago for an older Minecraft-Version. I recently found it again, started porting it to 1.21 and decided to upload 
    it to Github as a small memory for myself. I think I won't put much work into it as soon as i finished the TODOs, so dont expect anything great.
    <br />
    <a><strong>Explore the docs »</strong> <italic>There, sadly, are no docs yet.</italic></a>
    <br />
    <br />
    <a href="https://github.com/ventority/LPrefixor/issues/new?labels=bug&template=bug-report---.md">Report Bug</a>
    ·
    <a href="https://github.com/ventority/LPrefixor/issues/new?labels=enhancement&template=feature-request---.md">Request Feature</a>
  </p>
</div>



<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
    <li><a href="#acknowledgments">Acknowledgments</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project

<!-- [![Product Name Screen Shot][product-screenshot]](https://example.com) -->

The Plugin is based on LuckPerms capabillity to add custom prefixes. It basically just changes the ChatColor the prefix gets assigned.
To do that,  the command `/prefix` is added which allows you to change the color of the prefix a player has. More on the usage down below. Permissions for each color 
are created when a new color is added and can be given or taken by admins <italic>(This functionality is currently not working, the fix should be 
fairly easy tho. I will push an updated version soon). </italic> Keep in mind that Vanilla Minecfaft doesn't show prefixes at all so you will need tomething like TAB thst displays them in the Tab list and the chat. LPrefixor also doesn't sort the groups so you will have to do that within LuckPerm yourself if you want an Admin at the top and a player at the bottom.
<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- GETTING STARTED -->
### Installation
As every Spigot Plugin
1. Download the newest .jar file in the Releases-Tab
2. Download LuckPerms
3. Place both .jar files inside your Spigot-Server's plugins-folder
4. Enjoy

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- USAGE EXAMPLES -->
## Usage

`/prefix` - Opens a GUI of all available Colors the Player can choose from.<br>
`/prefix add <Name> <Hex Color> <Item>`- This Command adds the color specified using the hex color code with a given name and an assigned Item that's shown in the GUI.<br>
`/prefix remove <Name>`- Use this one to remove the color specified using the given name.


<!-- ROADMAP -->
## Roadmap

- [ ] Fixing Permission Checks
- [ ] (Re-)adding fade option so prefixes look even cooler 
- [ ] Adding usefull JavaDocs
- [ ] probably other stuff I currently dont think about. 

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- CONTRIBUTING -->
## Contributing

Contributions are what make the open source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

If you have a suggestion that would make this better, please fork the repo and create a pull request. You can also simply open an issue with the tag "enhancement".
Don't forget to give the project a star! Thanks again!

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- LICENSE -->
## License

Distributed under the GNU GPLv3 License. See `LICENSE.txt` for more information.

<p align="right">(<a href="#readme-top">back to top</a>)</p>




<!-- ACKNOWLEDGMENTS -->
## Acknowledgments

* []() <a href="https://github.com/othneildrew">othneildrew</a> for the amazing README template

<p align="right">(<a href="#readme-top">back to top</a>)</p>



