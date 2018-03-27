using AutoMapper;
using Controller;
using Model.Entity_Classes;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Web.Http;
using WifiNetworkLocalizer.Model.Database_Handlers;
using WifiNetworkLocalizer.Model.Message_Types;

namespace WifiNetworkLocalizer.Controller
{
    [RoutePrefix("localization")]
    public class LocalizationController : ApiController
    {
        private ILocalization _localizationServices;
        //private IMapper _mapperServices;

        public LocalizationController(ILocalization localizationServices)
        {
            _localizationServices = localizationServices;
           // _mapperServices = mapperServices;
        }

        [HttpGet]
        [Route("point")] //?firstMacId=xxx&secondMacId=yyy&thirsMacId=zzz
        public IHttpActionResult GetXYLocalizationPoint
            ([FromUri] string firstMacId, [FromUri] string secondMacId, [FromUri] string thirdMacId)
        {
            var request = new Model.Message_Types.DeterminantMacIds
            {
                FirstMacId = firstMacId,
                SecondMacId = secondMacId,
                ThirdMacId = thirdMacId
            };

            var data = _localizationServices.GetXYLocalizationPoint(request);

            return Ok(data);
        }

        [HttpGet]
        [Route("measurmentIds")] 
        public IHttpActionResult GetThreeMeasurmentMacIds()
        {
            var data = _localizationServices.GetThreeMeasurmentMacIds();

            //DeterminantMacIds result = 
            //    (data == null) ? null : _mapperServices.Map<Model.Message_Types.DeterminantMacIds, DeterminantMacIds>(data);

            return Ok(data);
        }

        [HttpGet]
        [Route("buildings")]
        public IHttpActionResult GetPossibleRooms()
        {
            var data = _localizationServices.GetPossibleRooms();

            return Ok(data);
        }

        [HttpPost]
        [Route("measurmentIds")]
        public IHttpActionResult SetThreeMeasurmentMacIds([FromBody] DeterminantMacIds threeMacIds)
        {
            //var request = _mapperServices.Map<DeterminantMacIds, Model.Message_Types.DeterminantMacIds>(threeMacIds);
            _localizationServices.SetThreeMeasurmentMacIds(threeMacIds);

            return Ok();
        }

        [HttpPost]
        [Route("xyPoints")]
        public IHttpActionResult AddRSSIMeasurmentInXYPoint([FromBody] RSSIMeasurmentPoint RSSIMeasurmentPoint)
        {
            _localizationServices.AddRSSIMeasurmentInXYPoint(RSSIMeasurmentPoint);

            return Ok();
        }
    }
}
