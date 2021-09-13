# Exchange rate scraper GitHub host page

### Some debug stuff here

site url is {{ site.url }}

<ul>
  {% for post in site.posts %}
    <li>
      <a href="{{site.url}}/{{ post.url }}">{{ post.title }}</a>
    </li>
  {% endfor %}
</ul>

❗ Detailed usage instruction in progress
