using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Web.Http;

namespace WifiNetworkLocalizer.Controller
{
    [RoutePrefix("localization")]
    public class LocalizationController : ApiController
    {
        [HttpGet]
        [Route("point")] //?firstMacId=xxx&secondMacId=yyy&thirsMacId=zzz
        public IHttpActionResult GetLocalizationPoint
            ([FromUri] string firstMacId, [FromUri] string secondMacId, [FromUri] string thirdMacId)
        {


            return Ok();
        }

        [HttpGet]
        [Route("measurmentIds")] //?buildingName=MCHTR
        public IHttpActionResult GetThreeMeasurmentMacIds([FromUri] string buildingName)
        {
            return Ok();
        }

        [HttpGet]
        [Route("buildings")]
        public IHttpActionResult GetPossibleBuildings()
        {
            return Ok();
        }
    }
}
