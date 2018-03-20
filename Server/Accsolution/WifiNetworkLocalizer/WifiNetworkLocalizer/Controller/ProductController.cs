using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Web.Http;
using WifiNetworkLocalizer.Model;

namespace WifiNetworkLocalizer.Controller
{
    public class ProductController : ApiController
    {
        Product[] products = new Product[]
        {
            new Product { Id = 1, Name = "Tomato Soup", Category = "Groceries", Price = 1 },
            new Product { Id = 2, Name = "Yo-yo", Category = "Toys", Price = 3.75M },
            new Product { Id = 3, Name = "Hammer", Category = "Hardware", Price = 16.99M }
        };

        [HttpGet]
        [Route("products")]
        public IHttpActionResult GetAllProduct()
        {
            return Ok(products);
        }

        [HttpGet]
        [Route("products/{id}")]
        public IHttpActionResult GetProduct([FromUri] int id)
        {
            return Ok(products[id]);
        }
    }
}
