using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Web.Http;
using WifiNetworkLocalizer.Model.Database_Handlers;

namespace WifiNetworkLocalizer.Controller
{
    [RoutePrefix("localization")]
    public class LocalizationController : ApiController
    {
        private ILocalization _localizationServices;

        public LocalizationController(ILocalization localizationServices)
        {
            this._localizationServices = localizationServices;
        }

        [HttpGet]
        [Route("point")] //?firstMacId=xxx&secondMacId=yyy&thirsMacId=zzz
        public IHttpActionResult GetXYLocalizationPoint
            ([FromUri] string firstMacId, [FromUri] string secondMacId, [FromUri] string thirdMacId)
        {
            var request = new Model.Message_Types.ThreeMacIds
            {
                FirstMacId = firstMacId,
                SecondMacId = secondMacId,
                ThirdMacId = thirdMacId
            };

            var data = _localizationServices.GetXYLocalizationPoint(request);
            return Ok(data);
        }

        [HttpGet]
        [Route("measurmentIds")] //?buildingName=MCHTR
        public IHttpActionResult GetThreeMeasurmentMacIds([FromUri] string buildingName)
        {
            var data = _localizationServices.GetThreeMeasurmentMacIds(buildingName);
            return Ok(data);
        }

        [HttpGet]
        [Route("buildings")]
        public IHttpActionResult GetPossibleBuildings()
        {
            var data = _localizationServices.GetPossibleBuildings();
            return Ok(data);
        }
    }
}
